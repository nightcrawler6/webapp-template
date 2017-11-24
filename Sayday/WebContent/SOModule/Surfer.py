import requests
import lxml.html as magic
import Utils
import sys

GOOGLE = "http://www.google.com/search?q=stack+overflow+"
SUFFIX = "&btnI"

class Surfer:
    def __init__(self, debug):
        self.url = None
        self.source = None
        self.sourceObject = None
        self.debug = debug

    def __str__(self):
        return "StackOverflow Surfer: " + self.url

    def get_source(self):
        if Utils.noneorempty(self.url):
            raise NameError('URL was not set properly!')
        if Utils.noneorempty(self.source):
            self.source = requests.get(self.url).content
        return self.source

    def get_source_object(self):
        if Utils.noneorempty(self.sourceObject):
            if Utils.noneorempty(self.sourceObject):
                self.source = self.get_source()
            self.sourceObject = magic.fromstring(self.source)
        return self.sourceObject

    def get_best_answer(self):
        best_answer_xpath = '//span[@class="' \
                            'vote-accepted-on load-' \
                            'accepted-answer-date"]/..' \
                            '/../../td[@class="answercell"]' \
                            '/div'

        best_answer_element = self.sourceObject.xpath(best_answer_xpath)
        best_answer = ""
        if len(best_answer_element) == 0:
			if self.debug:
				print "No best answer yet!"
			return None
        for elem in best_answer_element[0]:
            best_answer += magic.tostring(elem)
        return best_answer

    def get_question_description(self):
        question_xpath = '//td[@class="postcell"]//div[@class="post-text"]'
        question_element = self.sourceObject.xpath(question_xpath)
        question = ""
        if len(question_element) == 0:
			if self.debug:
				print "Could not find question!"
			return None
        question += magic.tostring(question_element[0])

        return question

    def get_title(self):
        title_xpath = '//div[@id="question-header"]/h1/a'
        title_element = self.sourceObject.xpath(title_xpath)
        title = ""
        if len(title_element) == 0:
			if self.debug:
				print "No title? hmmmm weird..."
			return None
        title += magic.tostring(title_element[0])

        return Utils.cleanhtml(title)

    def get_lucky(self, query_question):
        query_question = query_question.replace(" ", "+")
        url_to_get_lucky = GOOGLE + query_question + SUFFIX
        if self.debug:
            print "BROWSING TO: " + url_to_get_lucky
        get = requests.get(url_to_get_lucky)
        html = magic.fromstring(get.content)
        bingo1 = None
        bingo2 = None
        try:
            bingo1 = html.xpath("//a[@dir='ltr']/@href")[0].split("=")[1]
            if "stackoverflow.com" in bingo1:
                return bingo1
        except:
        	if self.debug:
				print "Could not fetch search from source, checking redirects..."
        bingo2 = get.url
        return bingo2

    def think_it_through(self):
        return None

    def get_url(self):
        return self.url

    def set_url(self, url):
        self.url = url
        self.get_source()
        self.get_source_object()


so = Surfer(False)

#user = raw_input("What do you need to know? --> ")
user = sys.argv[1]

lucky_charm = so.get_lucky(user)

so.set_url(lucky_charm)

mytitle = so.get_title()
myquestion = so.get_question_description()
myanswer = so.get_best_answer()
overall = {}
overall['title'] = mytitle
overall['question'] = myquestion
overall['answer'] = myanswer

print overall
