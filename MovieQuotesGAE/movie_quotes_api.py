'''
Created on Feb 2, 2015

@author: joakes
'''
import endpoints
import protorpc
from models import MovieQuote
import main_bootstrap

@endpoints.api(name="moviequotes", version="v1", description="Movie Quotes API")
class MovieQuotesApi(protorpc.remote.Service):
    ''' Endpoints API for insert, list, and delete '''
    
    @MovieQuote.method(name="moviequote.insert", path="moviequote/insert", http_method="POST")
    def insert_quote(self, request):
        # use from_datastore field not method
        if request.from_datastore:
            my_quote = request
        else: 
            my_quote = MovieQuote(quote=request.quote, movie=request.movie, parent=main_bootstrap.PARENT_KEY) 
        my_quote.put()
        return my_quote
    
    @MovieQuote.query_method(name="moviequote.list", path="moviequote/list", http_method="GET", query_fields=("limit", "order", "pageToken"))
    def moviequote_list(self, query):
        return query
    
    # Note the training comma to force a 1-element tuple
    @MovieQuote.method(name="moviequote.delete", path="moviequote/delete/{entityKey}", http_method="DELETE", request_fields=("entityKey",))
    def moviequote_delete(self, request):
        if not request.from_datastore:
            raise endpoints.NotFoundException
        request.key.delete()
        return MovieQuote(quote="deleted")        
        
app = endpoints.api_server([MovieQuotesApi], restricted=False)
