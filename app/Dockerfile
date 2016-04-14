FROM php:5.6-cli
# phpize deps
RUN apt-get update && apt-get install -y \
		git \
    zip \
    unzip \
	--no-install-recommends && rm -r /var/lib/apt/lists/*
COPY . /opt/app
WORKDIR /opt/app
RUN curl -sS https://getcomposer.org/installer | php
RUN php composer.phar install
CMD [ "php", "-S", "0.0.0.0:8080", "./web/index.php" ]
