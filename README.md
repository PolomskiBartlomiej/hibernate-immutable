# hibernate-immutable
_Reference_: https://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/annotations/Immutable.html  
_Reference_: http://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#entity-immutability

# preface
The `@Immutable` annotation is used to specify that the annotated **entity,
attribute or collection** is immutable.

* An immutable entity may not be updated by the application. Updates to 
an immutable entity will be ignored, but no exception is thrown. 
`@Immutable` must be used on root entities only.
* `@Immutable` placed on a collection makes the collection immutable, 
meaning additions and deletions to and from the collection are not 
allowed. A `HibernateException` is thrown in this case.
* An immutable attribute type will not be copied in the currently 
running Persistence Context in order to detect if the underlying 
value is dirty. As a result loading the entity will require less 
memory and checking changes will be much faster.

_Remark_: putting `@Immutable` over non-entity type attribute will cause:
`org.hibernate.AnnotationException: @Immutable on property not allowed. Only allowed on entity level or on a collection.`

# internals
Internally, Hibernate is going to perform several optimizations, such as:
* reducing memory footprint since there is no need to retain the dehydrated 
state for the dirty checking mechanism,
* speeding-up the Persistence Context flushing phase since immutable 
entities can skip the dirty checking process.

# project description
In `ImmutableTest` class we provide basic tests:
* `modifyImmutableEntity_thenSave`
* `modifyImmutableCollection_thenSave`
* `modifyMutableEntity_inImmutableCollection`

# summary
While immutable entity changes are simply discarded, modifying an 
immutable collection end up in a `HibernateException` being thrown.