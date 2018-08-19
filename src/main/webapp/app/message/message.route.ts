import { Route } from '@angular/router';

import { JhiMessageComponent } from './message.component';

export const MESSAGE_ROUTE: Route = {
    path: 'message',
    component: JhiMessageComponent,
    data: {
        pageTitle: 'Message Component'
    }
};
