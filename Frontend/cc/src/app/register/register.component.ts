import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {LoginService} from '../services/login.service';

@Component({
  selector: 'cc-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  readonly passwordRegex = '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';

  signUpForm: FormGroup;

  emailSent: boolean;
  conflict: boolean;

  constructor(private fb: FormBuilder, public loginService: LoginService) {

    this.signUpForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.pattern(this.passwordRegex)]],
      passwordConfirm: ['']
    });
    this.signUpForm.get('passwordConfirm').setValidators([Validators.required, this.SamePasswordValidator()]);
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

  register() {
    if (this.signUpForm.valid){
      this.loginService.register(this.signUpForm.get('username').value, this.signUpForm.get('email').value, this.signUpForm.get('password').value).subscribe(response =>
        this.emailSent = true, error => {
        console.log(error);
        if (error.err.status === 409){
          this.conflict = true;
        }
      });
    }
  }
}
