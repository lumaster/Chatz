import { NgModule } from '@angular/core';

import { ChatzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ChatzSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ChatzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ChatzSharedCommonModule {}
