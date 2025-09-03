import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchProfilesComponent } from './search-profiles.component';

describe('SearchProfilesComponent', () => {
  let component: SearchProfilesComponent;
  let fixture: ComponentFixture<SearchProfilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchProfilesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SearchProfilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
