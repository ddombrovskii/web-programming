import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from '@angular/common';

import {Project} from '../model/project'
import {ProjectService} from "../service/project.service";

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrl: './edit-project.component.css'
})
export class EditProjectComponent implements OnInit {

  title: string = "";
  edit: boolean = false;
  project: Project = {} as Project;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private location: Location) {
  }

  ngOnInit() {
    this.getMode();
    if (this.edit) {
      this.getProject();
    }
  }

  getMode(): void {
    if (this.router.url.slice(-1) == 'w') {
      this.title = "Создание нового проекта";
      this.edit = false;
    } else {
      this.title = "Реадктирование проекта";
      this.edit = true;
    }
  }

  getProject(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.projectService.getProject(id)
      .subscribe(project => this.project = project);
  }

  goBack(): void {
    this.location.back();
  }

  update(): void {
    if (this.project) {
      this.projectService.updateProject(this.project)
        .subscribe(() => this.goBack());
    }
  }

  save(): void {
    if (this.project) {
      this.projectService.createProject(this.project)
        .subscribe(() => this.goBack());
    }
  }


}
