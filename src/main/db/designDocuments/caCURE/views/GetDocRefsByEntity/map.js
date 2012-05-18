function(doc){
    if (doc.entityId )
    {
        emit(doc.entityId, { '_id':doc._id, '_rev':doc._rev });
    }
}