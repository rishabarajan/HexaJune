import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html', // Fixed file name
  styles: []
})
export class AppComponent { // Fixed class name (should be AppComponent)
  title = 'asset-management-ui6';
}