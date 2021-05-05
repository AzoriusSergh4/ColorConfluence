import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';

export interface CCError {
  message: string;
  err: any;
}

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(protected http: HttpClient) { }

  /**
   * Common method to perform the GET request
   * @param url the relative url of the request
   * @param headers optional headers to add
   * @protected
   */
  protected apiGetRequest(url: string, headers?: HttpHeaders){

    console.log(environment.apiUrl + url);
    const options = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With'
      })
    };
    if (headers) {
      headers.keys().forEach(key => {
        options.headers = options.headers.append(key, headers.get(key));
      });
    }
    return this.http.get<any>(environment.apiUrl + url, options)
      .pipe(
        map(result => result),
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Common method to perform the POST request
   * @param url the relative url of the request
   * @param body optional body
   * @param headers optional headers to add
   * @protected
   */
  protected apiPostRequest(url: string, body?: string, headers?: HttpHeaders){

    console.log(environment.apiUrl + url);
    const options = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With'
      })
    };
    if (headers) {
      headers.keys().forEach(key => {
        options.headers = options.headers.append(key, headers.get(key));
      });
    }
    if (body){
      return this.http.post<any>(environment.apiUrl + url, body, options)
        .pipe(
          map(result => result),
          catchError(err => this.handleError(err))
        );
    } else {
      return this.http.post<any>(environment.apiUrl + url, options)
        .pipe(
          map(result => result),
          catchError(err => this.handleError(err))
        );
    }
  }

  /**
   * Common method to perform the GET request
   * @param url the relative url of the request
   * @param criteria
   * @protected
   */
  protected apiGetRequestWithCriteria(url: string, criteria: HttpParams){
    console.log(environment.apiUrl + url);
    const options = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With'
      }),
      params: criteria
    };
    return this.http.get<any>(environment.apiUrl + url, options)
      .pipe(
        map(result => result),
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Common method to handle response errors
   * @param error the error
   * @protected
   */
  protected handleError(error: any){
    console.error(error);
    const e: CCError = {
      message: 'Server error (' + error.status + ')',
      err: error
    };
    return throwError(e);
  }
}
