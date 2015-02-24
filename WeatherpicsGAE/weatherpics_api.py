'''
Created on Feb 2, 2015

@author: joakes
'''
import endpoints
import protorpc
from models import Weatherpic
import main

@endpoints.api(name="weatherpics", version="v1", description="Weather Pic API")
class WeatherPicsApi(protorpc.remote.Service):
    ''' Endpoints API for insert, list, and delete '''
    
    @Weatherpic.method(name="weatherpic.insert", path="weatherpic/insert", http_method="POST")
    def insert_weatherpic(self, request):
        # use from_datastore field not method
        if request.from_datastore:
            wp = request
        else: 
            wp = Weatherpic(image_url=request.image_url, caption=request.caption, parent=main.PARENT_KEY) 
        wp.put()
        return wp
    
    @Weatherpic.query_method(name="weatherpic.list", path="weatherpic/list", http_method="GET", query_fields=("limit", "order", "pageToken"))
    def weatherpic_list(self, query):
        return query
    
    # Note the training comma to force a 1-element tuple
    @Weatherpic.method(name="weatherpic.delete", path="weatherpic/delete/{entityKey}", http_method="DELETE", request_fields=("entityKey",))
    def weatherpic_delete(self, request):
        if not request.from_datastore:
            raise endpoints.NotFoundException
        request.key.delete()
        return Weatherpic(caption="deleted")        
        
app = endpoints.api_server([WeatherPicsApi], restricted=False)
