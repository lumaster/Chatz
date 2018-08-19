import { Routes } from '@angular/router';

import { LoginComponent } from 'app/login/login.component';
import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
    {
        path: 'error',
        component: ErrorComponent,
        data: {
            authorities: [],
            pageTitle: 'Shop Chat - Error'
        }
    },
    {
        path: 'accessdenied',
        component: LoginComponent,
        data: {
            authorities: [],
            pageTitle: 'Shop Chat - Please Login',
            error403: true
        }
    }
];
