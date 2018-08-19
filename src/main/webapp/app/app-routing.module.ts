import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { HomeComponent } from 'app/home';
import { UserRouteAccessService } from 'app/core';
import { LoginComponent } from 'app/login/login.component';

const LAYOUT_ROUTES = [
    ...errorRoute,
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '',
        component: HomeComponent,
        data: {
            authorities: ['ROLE_ADMIN']
        },
        canActivate: [UserRouteAccessService]
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#ChatzAdminModule'
                }
            ],
            { useHash: true }
        )
    ],
    exports: [RouterModule]
})
export class ChatzAppRoutingModule {}
