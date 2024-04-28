import {Injectable} from '@angular/core';
import {catchError, map, Observable, of, Subscription, tap} from 'rxjs';
import {Project} from '../model/project';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {
  }

  getProject(id: number): Observable<Project> {
    return this.http.get<Project>(environment.apiUrl + `/projects/${id}`);
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(environment.apiUrl + '/projects?search=')
      .pipe(
        tap(_ => console.log('fetched projects')),
        catchError(this.handleError<Project[]>('getAllProjects', []))
      );
  }

  createProject(project: Project): Observable<Project> {
    return this.http.post<Project>(environment.apiUrl + '/projects', project, this.httpOptions);
  }

  updateProject(project: Project): Observable<Project> {
    return this.http.put<Project>(environment.apiUrl + `/projects/${project.id}`, project, this.httpOptions);
  }

  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(environment.apiUrl + `/projects/${id}`);
  }

  getOpenTasks(): Observable<Record<number, number>> {
    return this.http.get<Record<number, number>>(environment.apiUrl + '/projects/open-tasks');
  }

  searchProject(value: string): Observable<Project[]> {
    return this.http.get<Project[]>(environment.apiUrl + '/projects?search=' + value);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
