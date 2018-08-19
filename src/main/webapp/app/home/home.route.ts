import { Route } from '@angular/router';

import { HomeComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const HOME_ROUTE: Route = {
    path: 'home',
    component: HomeComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'Welcome to Shop Chat'
    },
    canActivate: [UserRouteAccessService]
};
