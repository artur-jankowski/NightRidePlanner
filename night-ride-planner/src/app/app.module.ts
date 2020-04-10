import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Routes, RouterModule } from '@angular/router';

import { MatSliderModule } from '@angular/material/slider'
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { GroupOverviewComponent } from './group-overview/group-overview.component';
import { GroupCreateComponent } from './group-create/group-create.component';
import { MenuComponent } from './menu/menu.component';
import { GroupListComponent } from './group-list/group-list.component';
import { RegisterComponent } from './register/register.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import { GroupComponent } from './group/group.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginActivateGuard } from './activates/login-activate.guard';
import { UserOverviewComponent } from './user-overview/user-overview.component';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';

const routes: Routes = [
  { path: 'groups', component: GroupComponent, canActivate: [LoginActivateGuard] },
  { path: 'groups/create', component: CreateGroupComponent, canActivate: [LoginActivateGuard] },
  { path: 'user', component: UserOverviewComponent, canActivate: [LoginActivateGuard] },
  { path: 'event', component: AppComponent, canActivate: [LoginActivateGuard] },
  { path: 'event/create', component: AppComponent, canActivate: [LoginActivateGuard] }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    GroupOverviewComponent,
    GroupCreateComponent,
    GroupListComponent,
    RegisterComponent,
    CreateGroupComponent,
    GroupComponent,
    MenuComponent,
    WelcomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes, { useHash: false }),
    HttpClientModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatMenuModule,
    MatToolbarModule,
    MatStepperModule,
    MatButtonModule,
    MatListModule,
    MatGridListModule,
    MatCardModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
