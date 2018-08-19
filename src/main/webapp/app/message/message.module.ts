import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatzSharedModule } from 'app/shared';
import { MESSAGE_ROUTE, JhiMessageComponent } from './';

@NgModule({
    imports: [ChatzSharedModule, RouterModule.forChild([MESSAGE_ROUTE])],
    declarations: [JhiMessageComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatzMessageModule {}
