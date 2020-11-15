from streamparse.bolt import Bolt

import requests

url = 'http://localhost:5001/updateData'

class TweetUpdateDashboard(Bolt):

	def initialize(self, conf, context):
		self._conf = conf
		self._context = context

	def process(self, tup):
		word = tup.values[0]
		data = {'word': word}
		requests.post(url, data = data)

TweetUpdateDashboard().run()