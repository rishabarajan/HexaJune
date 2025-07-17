import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { 
  provideHttpClient, 
  withInterceptors, 
  withJsonpSupport,
  withFetch,
  withInterceptorsFromDi
} from '@angular/common/http';
import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth-interceptor';
//import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [
    // Router configuration
    provideRouter(routes),
    
    // HTTP client with interceptors and features
    provideHttpClient(
      withInterceptors([authInterceptor]),
      withJsonpSupport(),
      withFetch(),
      withInterceptorsFromDi()
    ),
    
    // Browser animations
   // importProvidersFrom(BrowserAnimationsModule),
    
    // Other application-wide providers can be added here
  ]
};