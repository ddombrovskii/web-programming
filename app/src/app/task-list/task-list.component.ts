import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../service/project.service";
import {TaskService} from "../service/task.service";
import {Project} from "../model/project";
import {Task} from '../model/task';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  project: Project = {} as Project;

  currentDate: number = Date.now();

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.getProject(id);
    this.getTasks(id);
  }

  getProject(projectId: number): void {
    this.projectService.getProject(projectId)
      .subscribe(project => this.project = project);
  }

  getTasks(projectId: number): void {
    this.taskService.getAllTasks(projectId)
      .subscribe(tasks => this.tasks = tasks);
  }

  deleteTask(task: Task) {
    this.tasks = this.tasks.filter(t => t !== task);
    this.taskService.deleteById(this.project.id, task.id).subscribe();
  }

  convertDate(date: Date): number {
    return new Date(date).getTime();
  }
}
