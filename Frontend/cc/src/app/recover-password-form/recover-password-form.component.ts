import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';

@Component({
  selector: 'cc-recover-password-form',
  templateUrl: './recover-password-form.component.html',
  styleUrls: ['./recover-password-form.component.css']
})
export class RecoverPasswordFormComponent implements OnInit {

  recoverForm: FormGroup;
  readonly passwordRegex = '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';
  @Input() oldPassword: boolean;
  @Output() formEvent = new EventEmitter<FormGroup>();

  constructor(private fb: FormBuilder) {
    this.recoverForm = this.fb.group({
      oldPassword: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.pattern(this.passwordRegex)]],
      passwordConfirm: ['']
    });
    this.recoverForm.get('passwordConfirm').setValidators([Validators.required, this.SamePasswordValidator()]);
  }

  ngOnInit(): void {
  }

  public SamePasswordValidator(): ValidatorFn{
    return (control: FormControl): {[key: string]: boolean} => {
      const notSame = control.get('password') !== control.get('passwordConfirm');
      return notSame ? {
        notSame: true
      } : null ;
    };
  }

  sendForm(){
    this.formEvent.emit(this.recoverForm);
  }
}
