package KBaseKnowledgeEngine::KBaseKnowledgeEngineClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

KBaseKnowledgeEngine::KBaseKnowledgeEngineClient

=head1 DESCRIPTION


A KBase module: KBaseKnowledgeEngine


=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => KBaseKnowledgeEngine::KBaseKnowledgeEngineClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my %arg_hash2 = @args;
	if (exists $arg_hash2{"token"}) {
	    $self->{token} = $arg_hash2{"token"};
	} elsif (exists $arg_hash2{"user_id"}) {
	    my $token = Bio::KBase::AuthToken->new(@args);
	    if (!$token->error_message) {
	        $self->{token} = $token->token;
	    }
	}
	
	if (exists $self->{token})
	{
	    $self->{client}->{token} = $self->{token};
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 getConnectorsStatus

  $return = $obj->getConnectorsStatus()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a reference to a list where each element is a KBaseKnowledgeEngine.ConnectorStatus
ConnectorStatus is a reference to a hash where the following keys are defined:
	user has a value which is a string
	obj_ref has a value which is a string
	obj_type has a value which is a string
	connector_app has a value which is a string
	connector_title has a value which is a string
	job_id has a value which is a string
	state has a value which is a string
	output has a value which is a string
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	queued_epoch_ms has a value which is an int
	started_epoch_ms has a value which is an int
	finished_epoch_ms has a value which is an int

</pre>

=end html

=begin text

$return is a reference to a list where each element is a KBaseKnowledgeEngine.ConnectorStatus
ConnectorStatus is a reference to a hash where the following keys are defined:
	user has a value which is a string
	obj_ref has a value which is a string
	obj_type has a value which is a string
	connector_app has a value which is a string
	connector_title has a value which is a string
	job_id has a value which is a string
	state has a value which is a string
	output has a value which is a string
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	queued_epoch_ms has a value which is an int
	started_epoch_ms has a value which is an int
	finished_epoch_ms has a value which is an int


=end text

=item Description



=back

=cut

 sub getConnectorsStatus
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getConnectorsStatus (received $n, expecting 0)");
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.getConnectorsStatus",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getConnectorsStatus',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getConnectorsStatus",
					    status_line => $self->{client}->status_line,
					    method_name => 'getConnectorsStatus',
				       );
    }
}
 


=head2 cleanConnectorErrors

  $obj->cleanConnectorErrors()

=over 4

=item Parameter and return types

=begin html

<pre>

</pre>

=end html

=begin text



=end text

=item Description



=back

=cut

 sub cleanConnectorErrors
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function cleanConnectorErrors (received $n, expecting 0)");
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.cleanConnectorErrors",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'cleanConnectorErrors',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method cleanConnectorErrors",
					    status_line => $self->{client}->status_line,
					    method_name => 'cleanConnectorErrors',
				       );
    }
}
 


=head2 getAppsStatus

  $return = $obj->getAppsStatus()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a reference to a list where each element is a KBaseKnowledgeEngine.AppStatus
AppStatus is a reference to a hash where the following keys are defined:
	user has a value which is a string
	app has a value which is a string
	app_title has a value which is a string
	job_id has a value which is a string
	state has a value which is a string
	output has a value which is a string
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	queued_epoch_ms has a value which is an int
	started_epoch_ms has a value which is an int
	finished_epoch_ms has a value which is an int
	scheduled_epoch_ms has a value which is an int

</pre>

=end html

=begin text

$return is a reference to a list where each element is a KBaseKnowledgeEngine.AppStatus
AppStatus is a reference to a hash where the following keys are defined:
	user has a value which is a string
	app has a value which is a string
	app_title has a value which is a string
	job_id has a value which is a string
	state has a value which is a string
	output has a value which is a string
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	queued_epoch_ms has a value which is an int
	started_epoch_ms has a value which is an int
	finished_epoch_ms has a value which is an int
	scheduled_epoch_ms has a value which is an int


=end text

=item Description



=back

=cut

 sub getAppsStatus
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getAppsStatus (received $n, expecting 0)");
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.getAppsStatus",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getAppsStatus',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getAppsStatus",
					    status_line => $self->{client}->status_line,
					    method_name => 'getAppsStatus',
				       );
    }
}
 


=head2 runApp

  $return = $obj->runApp($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseKnowledgeEngine.RunAppParams
$return is a KBaseKnowledgeEngine.RunAppOutput
RunAppParams is a reference to a hash where the following keys are defined:
	app has a value which is a string
	ref_mode has a value which is a KBaseKnowledgeEngine.boolean
boolean is an int
RunAppOutput is a reference to a hash where the following keys are defined:
	job_id has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseKnowledgeEngine.RunAppParams
$return is a KBaseKnowledgeEngine.RunAppOutput
RunAppParams is a reference to a hash where the following keys are defined:
	app has a value which is a string
	ref_mode has a value which is a KBaseKnowledgeEngine.boolean
boolean is an int
RunAppOutput is a reference to a hash where the following keys are defined:
	job_id has a value which is a string


=end text

=item Description

Execute KE-App.

=back

=cut

 sub runApp
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function runApp (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to runApp:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'runApp');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.runApp",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'runApp',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method runApp",
					    status_line => $self->{client}->status_line,
					    method_name => 'runApp',
				       );
    }
}
 


=head2 getConnectorState

  $return = $obj->getConnectorState($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseKnowledgeEngine.GetConnectorStateParams
$return is a string
GetConnectorStateParams is a reference to a hash where the following keys are defined:
	obj_ref has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseKnowledgeEngine.GetConnectorStateParams
$return is a string
GetConnectorStateParams is a reference to a hash where the following keys are defined:
	obj_ref has a value which is a string


=end text

=item Description



=back

=cut

 sub getConnectorState
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getConnectorState (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getConnectorState:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getConnectorState');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.getConnectorState",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getConnectorState',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getConnectorState",
					    status_line => $self->{client}->status_line,
					    method_name => 'getConnectorState',
				       );
    }
}
 


=head2 cleanAppData

  $obj->cleanAppData($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseKnowledgeEngine.CleanAppDataParams
CleanAppDataParams is a reference to a hash where the following keys are defined:
	app has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseKnowledgeEngine.CleanAppDataParams
CleanAppDataParams is a reference to a hash where the following keys are defined:
	app has a value which is a string


=end text

=item Description

Only admins can run this function.

=back

=cut

 sub cleanAppData
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function cleanAppData (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to cleanAppData:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'cleanAppData');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.cleanAppData",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'cleanAppData',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method cleanAppData",
					    status_line => $self->{client}->status_line,
					    method_name => 'cleanAppData',
				       );
    }
}
 


=head2 loadEventsForObjRef

  $return = $obj->loadEventsForObjRef($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseKnowledgeEngine.LoadEventsForObjRefInput
$return is a reference to a list where each element is a KBaseKnowledgeEngine.WSEvent
LoadEventsForObjRefInput is a reference to a hash where the following keys are defined:
	accessGroupId has a value which is an int
	accessGroupObjectId has a value which is a string
	version has a value which is an int
WSEvent is a reference to a hash where the following keys are defined:
	storageCode has a value which is a string
	accessGroupId has a value which is an int
	accessGroupObjectId has a value which is a string
	version has a value which is an int
	newName has a value which is a string
	timestamp has a value which is an int
	eventType has a value which is a string
	storageObjectType has a value which is a string
	storageObjectTypeVersion has a value which is an int
	isGlobalAccessed has a value which is an int
	processed has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseKnowledgeEngine.LoadEventsForObjRefInput
$return is a reference to a list where each element is a KBaseKnowledgeEngine.WSEvent
LoadEventsForObjRefInput is a reference to a hash where the following keys are defined:
	accessGroupId has a value which is an int
	accessGroupObjectId has a value which is a string
	version has a value which is an int
WSEvent is a reference to a hash where the following keys are defined:
	storageCode has a value which is a string
	accessGroupId has a value which is an int
	accessGroupObjectId has a value which is a string
	version has a value which is an int
	newName has a value which is a string
	timestamp has a value which is an int
	eventType has a value which is a string
	storageObjectType has a value which is a string
	storageObjectTypeVersion has a value which is an int
	isGlobalAccessed has a value which is an int
	processed has a value which is an int


=end text

=item Description



=back

=cut

 sub loadEventsForObjRef
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function loadEventsForObjRef (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to loadEventsForObjRef:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'loadEventsForObjRef');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseKnowledgeEngine.loadEventsForObjRef",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'loadEventsForObjRef',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method loadEventsForObjRef",
					    status_line => $self->{client}->status_line,
					    method_name => 'loadEventsForObjRef',
				       );
    }
}
 
  
sub status
{
    my($self, @args) = @_;
    if ((my $n = @args) != 0) {
        Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
                                   "Invalid argument count for function status (received $n, expecting 0)");
    }
    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
        method => "KBaseKnowledgeEngine.status",
        params => \@args,
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
                           code => $result->content->{error}->{code},
                           method_name => 'status',
                           data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
                          );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method status",
                        status_line => $self->{client}->status_line,
                        method_name => 'status',
                       );
    }
}
   

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "KBaseKnowledgeEngine.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'loadEventsForObjRef',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method loadEventsForObjRef",
            status_line => $self->{client}->status_line,
            method_name => 'loadEventsForObjRef',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for KBaseKnowledgeEngine::KBaseKnowledgeEngineClient\n";
    }
    if ($sMajor == 0) {
        warn "KBaseKnowledgeEngine::KBaseKnowledgeEngineClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 boolean

=over 4



=item Description

A boolean. 0 = false, other = true.


=item Definition

=begin html

<pre>
an int
</pre>

=end html

=begin text

an int

=end text

=back



=head2 ConnectorStatus

=over 4



=item Description

state - one of queued, started, finished, error.
output - either empty for queued/started states or error message for error state or output message for finished.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
user has a value which is a string
obj_ref has a value which is a string
obj_type has a value which is a string
connector_app has a value which is a string
connector_title has a value which is a string
job_id has a value which is a string
state has a value which is a string
output has a value which is a string
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
queued_epoch_ms has a value which is an int
started_epoch_ms has a value which is an int
finished_epoch_ms has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
user has a value which is a string
obj_ref has a value which is a string
obj_type has a value which is a string
connector_app has a value which is a string
connector_title has a value which is a string
job_id has a value which is a string
state has a value which is a string
output has a value which is a string
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
queued_epoch_ms has a value which is an int
started_epoch_ms has a value which is an int
finished_epoch_ms has a value which is an int


=end text

=back



=head2 AppStatus

=over 4



=item Description

state - one of none, queued, started, finished, error.
output - either empty for queued/started states or error message for error state or output message for finished.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
user has a value which is a string
app has a value which is a string
app_title has a value which is a string
job_id has a value which is a string
state has a value which is a string
output has a value which is a string
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
queued_epoch_ms has a value which is an int
started_epoch_ms has a value which is an int
finished_epoch_ms has a value which is an int
scheduled_epoch_ms has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
user has a value which is a string
app has a value which is a string
app_title has a value which is a string
job_id has a value which is a string
state has a value which is a string
output has a value which is a string
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
queued_epoch_ms has a value which is an int
started_epoch_ms has a value which is an int
finished_epoch_ms has a value which is an int
scheduled_epoch_ms has a value which is an int


=end text

=back



=head2 RunAppParams

=over 4



=item Description

app - name of registered KB-SDK module configured to be compatible with KE.
ref_mode - flag for public reference data processing (accessible only for admins).


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app has a value which is a string
ref_mode has a value which is a KBaseKnowledgeEngine.boolean

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app has a value which is a string
ref_mode has a value which is a KBaseKnowledgeEngine.boolean


=end text

=back



=head2 RunAppOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
job_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
job_id has a value which is a string


=end text

=back



=head2 GetConnectorStateParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
obj_ref has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
obj_ref has a value which is a string


=end text

=back



=head2 CleanAppDataParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app has a value which is a string


=end text

=back



=head2 LoadEventsForObjRefInput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
accessGroupId has a value which is an int
accessGroupObjectId has a value which is a string
version has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
accessGroupId has a value which is an int
accessGroupObjectId has a value which is a string
version has a value which is an int


=end text

=back



=head2 WSEvent

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
storageCode has a value which is a string
accessGroupId has a value which is an int
accessGroupObjectId has a value which is a string
version has a value which is an int
newName has a value which is a string
timestamp has a value which is an int
eventType has a value which is a string
storageObjectType has a value which is a string
storageObjectTypeVersion has a value which is an int
isGlobalAccessed has a value which is an int
processed has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
storageCode has a value which is a string
accessGroupId has a value which is an int
accessGroupObjectId has a value which is a string
version has a value which is an int
newName has a value which is a string
timestamp has a value which is an int
eventType has a value which is a string
storageObjectType has a value which is a string
storageObjectTypeVersion has a value which is an int
isGlobalAccessed has a value which is an int
processed has a value which is an int


=end text

=back



=cut

package KBaseKnowledgeEngine::KBaseKnowledgeEngineClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
