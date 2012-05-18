function(doc)
{  
    if (doc.ownerId)
 	{   
	    emit(doc.ownerId, doc);
	}
}