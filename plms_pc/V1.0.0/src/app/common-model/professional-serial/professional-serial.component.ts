import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-professional-serial',
  templateUrl: './professional-serial.component.html',
  styleUrls: ['./professional-serial.component.css']
})
export class ProfessionalSerialComponent implements OnInit {

  @Input() professionalData:any=[];

  constructor() { }

  ngOnInit() {
  }

}
