import { Component } from '@angular/core';
import { TIMESTAMP } from 'app/app.constants';
import * as dayjs from 'dayjs';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
})
export class FooterComponent {
  buildTime: Date;

  constructor() {
    this.buildTime = dayjs(TIMESTAMP).toDate();
  }
}
