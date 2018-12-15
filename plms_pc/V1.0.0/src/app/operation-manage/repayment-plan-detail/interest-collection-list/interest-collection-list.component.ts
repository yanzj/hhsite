import { Component, OnInit,Input,} from '@angular/core';

@Component({
  selector: 'app-interest-collection-list',
  templateUrl: './interest-collection-list.component.html',
  styleUrls: ['./interest-collection-list.component.css']
})
export class InterestCollectionListComponent implements OnInit {
  @Input() repaymentSchedule:any={};
  constructor() { }

  ngOnInit() {
  }

}
