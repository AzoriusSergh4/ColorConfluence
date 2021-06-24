import {Component, Inject, OnInit} from '@angular/core';
import {BaseComponent} from '../base/base.component';
import {Router} from '@angular/router';
import {LoginService, User} from '../services/login.service';
import {DeckService} from '../services/deck.service';
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {Format} from '../deck-creation/deck-creation.component';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {CdkDragDrop} from '@angular/cdk/drag-drop';
import {SelectionModel} from '@angular/cdk/collections';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UtilsService} from '../services/utils.service';

export interface Folder {
  id: number;
  name: string;
  decks: TreeDeck[];
  folders: Folder[];
  folder: true;
  root: boolean;
  colors: undefined;
}

export interface TreeDeck {
  id: number;
  name: string;
  colors: string;
  format: Format;
  user: User;
  decks: TreeDeck[];
  folders: Folder[];
  folder: false;
}

interface FlatNode {
  id: number;
  expandable: boolean;
  name: string;
  level: number;
  colors: string;
}

@Component({
  selector: 'cc-deck-user',
  templateUrl: './deck-user.component.html',
  styleUrls: ['./deck-user.component.css']
})
export class DeckUserComponent extends BaseComponent implements OnInit {

  constructor(protected router: Router, public loginService: LoginService, public dialog: MatDialog, private snackBar: MatSnackBar, private deckService: DeckService, public utilsService: UtilsService) {
    super(router);
    if (!loginService.isLogged) {
      this.redirectToHome();
    }
    this.loadTree();
  }

  folder: Folder[];
  loadingContent: boolean;

  treeControl = new FlatTreeControl<FlatNode>(
    node => node.level, node => node.expandable
  );
  // expansion model tracks expansion state
  expansionModel = new SelectionModel<number>(true);
  private transformer = (node: Folder | TreeDeck, level: number) => {
    return {
      expandable: node.folder,
      name: node.name,
      level,
      id: node.id,
      colors: node.colors
    };
  }

  treeFlattener = new MatTreeFlattener(
    this.transformer, node => node.level, node => node.expandable, node => {
      let line: (Folder | TreeDeck)[];
      line = [];
      node.decks.forEach(d => line.push(d));
      node.folders.forEach(f => line.push(f));
      return line;
    });
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  ngOnInit(): void {
  }

  loadTree() {
    this.loadingContent = true;
    this.deckService.getUserDecks(this.loginService.user.id).subscribe(response => {
      this.folder = [];
      this.folder.push(response);
      this.folder.forEach(f => {
        this.initializeFolder(f);
      });
      this.folder[0].root = true;
      console.log(this.folder);
      this.dataSource.data = this.folder;
      this.loadingContent = false;
      this.treeControl.expand(this.treeControl.dataNodes[0]);
      const expandedNodes = this.expansionModel.selected;
      expandedNodes.forEach(id => {
        const node = this.treeControl.dataNodes.find((n) => n.id === id);
        this.treeControl.expand(node);
      });
      this.expansionModel.clear();
      this.expansionModel.select(this.folder[0].id);
      expandedNodes.forEach(id => {
        this.expansionModel.select(id);
      });
      console.log(this.dataSource);
    }, error => {
      this.loadingContent = false;
      console.log(error);
    });
  }

  initializeFolder(folder: Folder) {
    folder.folder = true;
    folder.root = false;
    folder.decks.forEach(d => {
      d.decks = [];
      d.folders = [];
    });
    folder.folders.forEach(f => {
      this.initializeFolder(f);
    });
  }

  isFolder = (_: number, node: FlatNode) => node.expandable;

  newDeck(folder: Folder) {
    this.router.navigate(['deck-editor'], {queryParams: {folderId: folder.id}});
  }

  openNewFolderDialog(folder: Folder) {
    console.log(folder);
    const dialogRef = this.dialog.open(NewEntityDialog, {
      width: '75%',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deckService.newFolder(folder.id, result).subscribe(response => {
          this.loadTree();
        }, error => {
          console.error(error);
        });
      }
    });
  }

  openDeleteFolderDialog(folder: Folder) {
    const dialogRef = this.dialog.open(DeleteEntityDialog, {
      width: '75%',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deckService.deleteFolder(folder.id).subscribe(() => {
          this.loadTree();
        }, error => {
          console.error(error);
        });
      }
    });
  }

  editDeck(deckId: number) {
    this.router.navigate(['/deck-editor'], {queryParams: {id: deckId}});
  }

  openDeleteDeckDialog(node: number) {
    const dialogRef = this.dialog.open(DeleteEntityDialog, {
      width: '50%',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deckService.deleteDeck(node).subscribe(() => {
          this.loadTree();
          this.snackBar.open('The deck was deleted successfully', 'OK', {duration: 3000});
        }, error => {
          this.snackBar.open('There was an error deleting the deck. Please try again later', 'OK', {duration: 3000});
          console.error(error);
        });
      }
    });
  }

  /**
   * This constructs an array of nodes that matches the DOM
   */
  visibleNodes(): (Folder | TreeDeck)[] {
    const nodes = [];

    function addExpandedChildren(node: Folder | TreeDeck, expanded: number[]) {
      nodes.push(node);
      if (expanded.includes(node.id)) {
        node.decks.map((child) => addExpandedChildren(child, expanded));
        node.folders.map((child) => addExpandedChildren(child, expanded));
      }
    }

    this.dataSource.data.forEach((node) => {
      addExpandedChildren(node, this.expansionModel.selected);
    });
    return nodes;
  }

  /**
   * Handle the drop event
   * @param event the event
   */
  drop(event: CdkDragDrop<string[]>) {
    // ignore drops outside of the tree, from or to the root folder, and same positions
    if (!event.isPointerOverContainer || event.previousIndex === 0 || event.currentIndex === 0 || event.previousIndex === event.currentIndex) {
      return;
    }
    const visibleNodes = this.visibleNodes();
    console.log(visibleNodes);
    // ignore drops from ancestor to child
    if (this.isAncestor(visibleNodes[event.previousIndex], visibleNodes[event.currentIndex])) {
      return;
    }
    // ignore drops to same folder
    if (visibleNodes[event.currentIndex].folder && visibleNodes[event.currentIndex] === findParent(visibleNodes[event.previousIndex].id, this.folder[0])) {
      return;
    }
    if (!visibleNodes[event.currentIndex].folder && findParent(visibleNodes[event.currentIndex].id, this.folder[0]) === findParent(visibleNodes[event.previousIndex].id, this.folder[0])) {
      return;
    }
    console.log(visibleNodes[event.previousIndex]);
    visibleNodes[event.currentIndex].folder ? console.log(visibleNodes[event.currentIndex]) : console.log(findParent(visibleNodes[event.currentIndex].id, this.folder[0]));

    function findParent(id: number, parent: TreeDeck | Folder): Folder | TreeDeck {

      let result;
      let subResult;
      parent.decks.forEach(item => {
        if (item.id === id) {
          result = parent;
        }
      });
      parent.folders.forEach(item => {
        if (item.id === id) {
          result = parent;
        } else {
          subResult = findParent(id, item);
          if (subResult) {
            result = subResult;
          }
        }
      });
      return result;
    }

    const to = visibleNodes[event.currentIndex].folder ? visibleNodes[event.currentIndex] : findParent(visibleNodes[event.currentIndex].id, this.folder[0]);
    if (visibleNodes[event.previousIndex].folder) {
      this.deckService.moveFolder(visibleNodes[event.previousIndex].id, to.id).subscribe(() => {
        this.loadTree();
      });
    } else {
      this.deckService.moveDeck(visibleNodes[event.previousIndex].id, to.id).subscribe(() => {
        this.loadTree();
      });
    }
  }

  isAncestor(parent: Folder | TreeDeck, child: Folder | TreeDeck): boolean {
    let ancestor: boolean;
    ancestor = false;
    parent.decks.forEach(d => {
      if (d.id === child.id) {
        ancestor = true;
      }
    });
    parent.folders.forEach(f => {
      if (f.id === child.id) {
        ancestor = true;
      } else {
        ancestor = this.isAncestor(f, child);
      }
    });
    return ancestor;
  }
}

@Component({
  selector: 'new-entity-dialog',
  templateUrl: 'new-entity-dialog.html',
})
// tslint:disable-next-line:component-class-suffix
export class NewEntityDialog {

  folderName: string;

  constructor(
    public dialogRef: MatDialogRef<NewEntityDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  closeDialog() {
    this.dialogRef.close(this.folderName);
  }
}


@Component({
  selector: 'delete-entity-dialog',
  templateUrl: 'delete-entity-dialog.html',
})
// tslint:disable-next-line:component-class-suffix
export class DeleteEntityDialog {

  constructor(
    public dialogRef: MatDialogRef<DeleteEntityDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

}
