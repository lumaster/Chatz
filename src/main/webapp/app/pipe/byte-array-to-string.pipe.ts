import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'byteArrayToString'
})
export class ByteArrayToStringPipe implements PipeTransform {
    transform(value: string): string {
        console.log('base64 : ' + value);
        return atob(value);
    }
}
