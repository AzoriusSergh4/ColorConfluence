import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

const API_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(protected http: HttpClient) { }

  /**
   * Common method to perform the GET request
   * @param url the relative url of the request
   * @protected
   */
  protected apiGetRequest(url: string){
    console.log(API_URL + url);
    const options = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With'
      })
    };
    return this.http.get<any>(API_URL + url, options)
      .pipe(
        map(result => result),
        catchError(err => this.handleError(err))
      );
  }

  /**
   * Common method to perform the GET request
   * @param url the relative url of the request
   * @param criteria
   * @protected
   */
  protected apiGetRequestWithCriteria(url: string, criteria: HttpParams){
    console.log(API_URL + url);
    const options = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With'
      }),
      params: criteria
    };
    return this.http.get<any>(API_URL + url, options)
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
    return throwError('Server error (' + error.status + ' ): ' + error);
  }
}
