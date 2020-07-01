import {DefaultCrudRepository} from '@loopback/repository';
import {Meeting, MeetingRelations} from '../models';
import {MysqldbDataSource} from '../datasources';
import {inject} from '@loopback/core';

export class MeetingRepository extends DefaultCrudRepository<
  Meeting,
  typeof Meeting.prototype.id,
  MeetingRelations
> {
  constructor(
    @inject('datasources.mysqldb') dataSource: MysqldbDataSource,
  ) {
    super(Meeting, dataSource);
  }
}
