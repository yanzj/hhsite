import { HonghuoPage } from './app.po';

describe('honghuo App', () => {
  let page: HonghuoPage;

  beforeEach(() => {
    page = new HonghuoPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
