import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-channel-interest-non-collection',
  templateUrl: './channel-interest-non-collection.component.html',
  styleUrls: ['./channel-interest-non-collection.component.css']
})
export class ChannelInterestNonCollectionComponent implements OnInit {
  @Input() repaymentSchedule:any={};
  constructor() { }

  ngOnInit() {
  }

}
