package com.safehouse.fizyoterapim.Firebase.Service

import com.safehouse.fizyoterapim.Firebase.Model.Response
import java.util.HashMap

class ChildService : AbsService() {

    var FORMCOLLECTIONNAME : String = AbsService.FORMCOLLECTIONNAME;
    var EGZERSİZCOLLECTIONNAME : String = AbsService.EGZERSİZCOLLECTIONNAME;
    var RANDEVUCOLLECTIONNAME : String = AbsService.RANDEVUCOLLECTIONNAME;
    var USERCOLLECTIONNAME : String = AbsService.RANDEVUCOLLECTIONNAME;

    override fun add(collectionName: String?, o: Any?): Response {
        return super.add(collectionName, o)
    }

    override fun get(
        collectionName: String?,
        queryName: String?,
        queryValue: String?,
        clss: Class<*>?
    ): Response {
        return super.get(collectionName, queryName, queryValue, clss)
    }

    override fun update(collectionName: String?, map: HashMap<String, Any>?, id: String?) {
        super.update(collectionName, map, id)
    }

    override fun delete(collectionName: String?, id: String?) {
        super.delete(collectionName, id)
    }

    companion object {
        lateinit var USERCOLLECTIONNAME: String
    }
}