import { Component, OnInit, OnDestroy } from '@angular/core';

import { JhiMessageService } from 'app/core';

@Component({
    selector: 'jhi-message',
    templateUrl: './message.component.html'
})
export class JhiMessageComponent implements OnInit, OnDestroy {
    activities: any[] = [];
    message: String;

    constructor(private messageService: JhiMessageService) {}

    showActivity(activity: any) {
        let existingActivity = false;
        for (let index = 0; index < this.activities.length; index++) {
            if (this.activities[index].sessionId === activity.sessionId) {
                existingActivity = true;
                if (activity.page === 'logout') {
                    this.activities.splice(index, 1);
                } else {
                    this.activities[index] = activity;
                }
            }
        }
        if (!existingActivity && activity.page !== 'logout') {
            this.activities.push(activity);
        }
    }

    sendMessage() {
        // alert('Sending message = ' + this.message);
        // this.messageService.sendMessage({message: this.message});
        this.messageService.sendMessage({ message: this.message });
    }

    ngOnInit() {
        this.messageService.subscribe();
        this.messageService.receive().subscribe(activity => {
            alert('Received message = ' + JSON.stringify(activity));
        });
    }

    ngOnDestroy() {
        this.messageService.unsubscribe();
    }
}
