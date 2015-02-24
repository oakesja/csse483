import protorpc
import endpoints
import main

from models import *

WEB_CLIENT_ID = "184498034026-03umn05pmaj9loadagn7h8klmgnb04oh.apps.googleusercontent.com"
ANDROID_CLIENT_ID = "184498034026-tbuu4k9rkrrdesfgansi7esf2oljmlbi.apps.googleusercontent.com"

@endpoints.api(name="graderecorder", version="v1", description="Grade Recorder API", audiences=[WEB_CLIENT_ID],
               allowed_client_ids=[endpoints.API_EXPLORER_CLIENT_ID, WEB_CLIENT_ID, ANDROID_CLIENT_ID])

class GradeRecorderApi(protorpc.remote.Service):
    @Student.query_method(user_required=True, query_fields=("limit", "pageToken"), 
                          name="student.list", path="student/list", http_method="GET")
    def student_list(self, query):
        user = endpoints.get_current_user()
        students = Student.query(ancestor=main.get_parent_key(user)).order(Student.rose_username)
        return students
    
    @Assignment.query_method(user_required=True, query_fields=("limit", "pageToken"), 
                             name="assignment.list", path="assignment/list", http_method="GET")
    def assignments_list(self, query):
        user = endpoints.get_current_user()
        assignments = Assignment.query(ancestor=main.get_parent_key(user)).order(Assignment.name)
        return assignments
    
    @GradeEntry.query_method(user_required=True, query_fields=("limit", "order", "pageToken", "assignment_key"), 
                            name="gradeentry.list", path="gradeentry/list/{assignment_key}", http_method="GET" )
    def gradeentry_list(self, query):
        return query
    
    @Assignment.method(user_required=True, name="assignment.insert", path="assignment/insert", http_method="POST")
    def assignment_insert(self, assignment):
        if assignment.from_datastore:
            assignment_with_parent = assignment
        else:
            assignment_with_parent = Assignment(parent=main.get_parent_key(endpoints.get_current_user()), 
                                                name=assignment.name)
        assignment_with_parent.put()
        return assignment_with_parent
    
    @GradeEntry.method(user_required=True, name="gradeentry.insert", path="gradeentry/insert", http_method="POST")
    def gradeentry_insert(self, grade_entry):
        if grade_entry.from_datastore:
            grade_entry_with_parent = grade_entry
        else:
            student = grade_entry.student_key.get()
            grade_entry_with_parent = GradeEntry(parent = grade_entry.assignment_key,
                                                 id = student.rose_username,
                                                 score = grade_entry.score,
                                                 student_key = grade_entry.student_key,
                                                 assignment_key = grade_entry.assignment_key
                                                 )
        grade_entry_with_parent.put()
        return grade_entry_with_parent
    
    @Assignment.method(user_required=True, request_fields=("entityKey",), 
                       name="assignment.delete", path="assignment/delete/{entityKey}", http_method="DELETE")
    def assignment_delete(self, assignment):
        if not assignment.from_datastore:
            raise endpoints.NotFoundException("No assignment found for the given key")
        children = GradeEntry.query(ancestor=assignment.key)
        for grade_entry in children:
            grade_entry.key.delete()
        assignment.key.delete()
        return Assignment(name="deleted")
    
    @GradeEntry.method(user_required=True, request_fields=("entityKey", ),
                       name="gradeentry.delete", path="gradeentry/delete/{entityKey}", http_method="DELETE")
    def gradeentry_delete(self, grade_entry):
        if not grade_entry.from_datastore:
            raise endpoints.NotFoundException("No grade entry found for the given key")
        grade_entry.key.delete()
        return GradeEntry()
        
        
app = endpoints.api_server([GradeRecorderApi], restricted=False)