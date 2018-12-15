import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-interest-non-collection-list',
  templateUrl: './interest-non-collection-list.component.html',
  styleUrls: ['./interest-non-collection-list.component.css']
})
export class InterestNonCollectionListComponent implements OnInit {
  @Input() repaymentSchedule:any={};
  constructor() { }

  ngOnInit() {
  }

}
