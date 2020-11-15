from streamparse.bolt import Bolt

class TweetUpdateDashboard(Bolt):

	def process(self, tup):

		print("PYTHON", tup)