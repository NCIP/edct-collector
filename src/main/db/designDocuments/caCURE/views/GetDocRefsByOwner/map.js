function(doc){
    if (doc.ownerId )
    {
        emit(doc.ownerId, { '_id':doc._id, '_rev':doc._rev });
    }
}