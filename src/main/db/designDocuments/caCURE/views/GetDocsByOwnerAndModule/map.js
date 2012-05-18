function(doc)
{  
    if (doc.ownerId && doc.moduleId)
 	{   
	    emit(doc.ownerId+"_"+doc.moduleId, doc);
	}
}