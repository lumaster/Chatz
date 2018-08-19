import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'sortByDate'
})
export class SortByDatePipe implements PipeTransform {
    transform(objectList: any[], fieldName: string): any[] {
        if (objectList !== undefined) {
            objectList.sort((a: any, b: any) => {
                if (a[fieldName] < b[fieldName]) {
                    return -1;
                } else if (a[fieldName] > b[fieldName]) {
                    return 1;
                } else {
                    return 0;
                }
            });
        }

        return objectList;
    }
}
