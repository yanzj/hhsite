import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bread-crumb',
  templateUrl: './bread-crumb.component.html',
  styleUrls: ['./bread-crumb.component.css']
})
export class BreadCrumbComponent implements OnInit {

  @Input() contact:any ={};

  constructor() { }

  ngOnInit() {
  }

}
