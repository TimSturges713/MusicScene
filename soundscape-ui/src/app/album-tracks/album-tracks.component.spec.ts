import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlbumTracksComponent } from './album-tracks.component';

describe('AlbumTracksComponent', () => {
  let component: AlbumTracksComponent;
  let fixture: ComponentFixture<AlbumTracksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlbumTracksComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AlbumTracksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
