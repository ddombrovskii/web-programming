import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectListComponent} from "./project-list/project-list.component";
import {EditProjectComponent} from "./edit-project/edit-project.component";
import {TaskListComponent} from "./task-list/task-list.component";

const routes: Routes = [
  {path: '', redirectTo: 'projects', pathMatch: 'full'},
  {path: "projects", component: ProjectListComponent},
  {path: "projects/:id", component: EditProjectComponent},
  {path: "projects/new", component: EditProjectComponent},
  {path: "tasks/:id", component: TaskListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
