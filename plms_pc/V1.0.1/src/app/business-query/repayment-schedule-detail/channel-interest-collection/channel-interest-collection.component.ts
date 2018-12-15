import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-channel-interest-collection',
  templateUrl: './channel-interest-collection.component.html',
  styleUrls: ['./channel-interest-collection.component.css']
})
export class ChannelInterestCollectionComponent implements OnInit {

  @Input() repaymentSchedule:any={};
  constructor() { }

  ngOnInit() {
  }

}
