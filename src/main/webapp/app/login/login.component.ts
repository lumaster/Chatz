import { Component, ElementRef, OnInit } from '@angular/core';
import { LoginService } from 'app/core/login/login.service';
import { JhiEventManager } from 'ng-jhipster';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Router } from '@angular/router';
import { JhiMainComponent } from 'app/layouts/main/main.component.ts';
import { UserRouteAccessService } from 'app/core';

@Component({
    selector: 'jhi-login',
    templateUrl: './login.component.html',
    styleUrls: ['login.css']
})
export class LoginComponent implements OnInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    pageTitle: 'Welcome to Shop Chat';
    displayLoading = false;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private router: Router,
        private mainComponent: JhiMainComponent,
        private userRouteAccessService: UserRouteAccessService
    ) {
        this.credentials = {};
        mainComponent.setShowHeaderAndFooter(false);

        // if (this.userRouteAccessService.checkLogin(['ROLE_ADMIN'], '')) {
        //
        //     this.router.navigate(['home']);
        //     console.log('aaaaaa');
        // }
        // console.log('bbbbbb');
    }

    ngOnInit() {
        // this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
    }

    login() {
        this.displayLoading = true;
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });
                this.displayLoading = false;
                this.router.navigate(['/home']);
            })
            .catch(() => {
                this.displayLoading = false;
                this.authenticationError = true;
            });
    }

    register() {
        this.router.navigate(['/register']);
    }

    requestResetPassword() {
        this.router.navigate(['/reset', 'request']);
    }
}
