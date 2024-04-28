import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Task} from '../model/task';
import {HttpClient} from "@angular/common/http";
import {environment} from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) {
  }

  getAllTasks(projectId: number): Observable<Task[]> {
    return this.http.get<Task[]>(environment.apiUrl + `/projects/${projectId}/tasks`);
  }

  deleteById(projectId: number, taskId: number): Observable<void> {
    return this.http.delete<void>(environment.apiUrl + `/projects/${projectId}/tasks/${taskId}`);
  }
}
