import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatzSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { PipeModule } from 'app/pipe';
import { MomentModule } from 'ngx-moment';
import { FileUploadModule } from 'ng2-file-upload';

@NgModule({
    imports: [ChatzSharedModule, PipeModule, MomentModule, FileUploadModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatzHomeModule {}
