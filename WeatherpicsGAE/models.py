from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext.key_range import ndb

class Weatherpic(EndpointsModel):
    _message_fields_schema = ("entityKey", "image_url", "caption", "last_touch_date_time")
    image_url = ndb.StringProperty()
    caption = ndb.StringProperty()
    last_touch_date_time = ndb.DateTimeProperty(auto_now=True);