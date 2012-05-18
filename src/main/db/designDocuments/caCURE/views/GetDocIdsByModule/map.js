function(doc)
{  
    if (doc.moduleId)
 	{   
	    emit(doc.moduleId, doc._id);
	}
}