import {Directive, ElementRef, HostListener} from "@angular/core";
import {Router} from "@angular/router";

declare var $: any;

@Directive({
  selector:'[reloadtrue]'
})

export class ReloadTrueDirective {

  private el: ElementRef;

  constructor(
    private router: Router,
    el: ElementRef){
    this.el = el.nativeElement;
  }

  @HostListener('click')
  onClick(){
    let curhref = $(this.el).attr("href");
    let start = window.location.href.indexOf("#");
    let s = window.location.href.substring(start);
    if(s == curhref){
      window.location.reload();
    }
  }
}
