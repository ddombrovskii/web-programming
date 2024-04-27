import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../service/project.service";
import {Project} from "../model/project";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  openTasks!: Record<number, number>;

  constructor(private projectService: ProjectService) {
  }

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects(): void {
    forkJoin([
      this.projectService.getAllProjects(),
      this.projectService.getOpenTasks()
    ]).subscribe(res => {
      this.projects = res[0];
      this.openTasks = res[1]
    })
  }

  deleteProject(project: Project): void {
    this.projects = this.projects.filter(p => p !== project);
    this.projectService.deleteProject(project.id).subscribe();
  }

  searchProject(value: string): void {
    this.projectService.searchProject(value).subscribe(res => this.projects = res);
  }

}
