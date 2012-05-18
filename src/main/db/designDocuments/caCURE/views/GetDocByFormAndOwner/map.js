function(doc)
{  
    if (doc.formId && doc.ownerId)
 	{   
    	emit([ doc.formId, doc.ownerId], doc);
	}
}