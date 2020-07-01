import {DefaultCrudRepository} from '@loopback/repository';
import {Person, PersonRelations} from '../models';
import {MysqldbDataSource} from '../datasources';
import {inject} from '@loopback/core';

export class PersonRepository extends DefaultCrudRepository<
  Person,
  typeof Person.prototype.id,
  PersonRelations
> {
  constructor(
    @inject('datasources.mysqldb') dataSource: MysqldbDataSource,
  ) {
    super(Person, dataSource);
  }
}
