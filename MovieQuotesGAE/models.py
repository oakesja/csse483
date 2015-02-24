'''
Created on Jan 29, 2015

@author: joakes
'''
from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext.key_range import ndb

class MovieQuote(EndpointsModel):
    _message_fields_schema = ("entityKey", "quote", "movie", "last_touch_date_time")
    movie = ndb.StringProperty()
    quote = ndb.StringProperty()
    last_touch_date_time = ndb.DateTimeProperty(auto_now=True);