angular.module('starter')

    .controller('MainCtrl', [ '$scope', '$state', '$location', '$http', 'auth', 'API', function($scope,$state,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;

        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.user = {};

        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.loggedUserId)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $state.go("error");
                }else{
                    $scope.user = data;
                }
            }).error(function(data){
                console.log("error");
            });

        $scope.source = "";
        $scope.id = "";
        $scope.text = "";
        $scope.messages = [ ];
        $scope.newMessages = [ ];

        $scope.source = new EventSource(API.WS_URL+$scope.loggedUserId);
        $scope.source.onopen = function(event){
            console.log("opened");
        };
        $scope.source.onmessage = function(event) {
            console.log(event.data);
            var message = JSON.parse(event.data);
            console.log(message);
            $scope.newMessages.push(message);
            $scope.$apply();
        };
        $scope.source.onerror = function(event){
            console.log("error");
        };
        $scope.source.onclose = function(event){
            console.log("closed");
        };
        
        $scope.flush = function(){
            $scope.messages = $scope.messages.concat($scope.newMessages);
            $scope.newMessages = [ ];
        };

        $scope.send = function(){
            var tmp = {
                id: $scope.loggedUserId,
                content: $scope.text,
                date: new Date().getTime()
            };

            $http.post(API.URL + API.POST_ENDPOINT,tmp,{"Content-Type":"application/json"})
                .success(function(data){
                    console.log("Posted successfully");
                    $scope.newMessages.push(data);
                    $scope.text = "";
                })
                .error(function(data){
                    console.log("Kinda erroneous");
                });
        };

    }]);
